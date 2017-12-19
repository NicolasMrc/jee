/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



$(document).ready(function(){
    var dirtyStagiaires = [];

    $("input[name='selectedStagiaire']").change(function(e){
       e.preventDefault();
       $("#details").removeClass("disabled");
       $("#details").attr("href", "etudiant/" + $(this).val());
    });

    $('.boolean-td').click(function(){
        if($(this).hasClass('red')){
            $(this).removeClass('red');
            $(this).addClass('green');
        } else {
            $(this).removeClass('green');
            $(this).addClass('red');
        }

        var idStagiaire = $(this).attr('id').split('_')[1];

        if($.inArray(idStagiaire, dirtyStagiaires) == -1){
            dirtyStagiaires.push(idStagiaire);
        }
        $(this).addClass('dirty');
    });

    $('.entreprise-td').change(function(){
        var idStagiaire = $(this).attr('id').split('_')[1];
        $('#adresse_' +idStagiaire ).text($(this).find("option:selected").data('adresse'))
        $(this).addClass('dirty');


        if($.inArray(idStagiaire, dirtyStagiaires) == -1){
            dirtyStagiaires.push(idStagiaire);
        }
    });

    $('.form-control').change(function(){
        $(this).addClass('dirty');
        var idStagiaire = $(this).attr('id').split('_')[1];

        if($.inArray(idStagiaire, dirtyStagiaires) == -1){
            dirtyStagiaires.push(idStagiaire);
        }
    });

    $('#validate').click(function(){
        if(dirtyStagiaires.length == 0){
            PNotify.prototype.options.styling = "fontawesome";

            new PNotify({
                type : 'error',
                title: 'Erreur',
                text: 'Aucun changement detecté'
            });
        } else {
            var stagiaireToUpdate = [];
            $.each(dirtyStagiaires, function(index, value){
                stagiaireToUpdate.push(getStagiaire(value));
            });
            saveChanges(stagiaireToUpdate);
        }
    });

    function getStagiaire(index){
        stagiaire = {};

        stagiaire.id = index;
        stagiaire.stage = {};
        stagiaire.stage.fin = $('#date-fin_' + index).val();
        stagiaire.stage.debut = $('#date-debut_'+ index).val();
        stagiaire.stage.noteCom = $('#note-com_'+ index).val();
        stagiaire.stage.noteTech = $('#note-tech_'+ index).val();
        stagiaire.stage.maitreDeStage = $('#maitre-de-stage_'+ index).val();
        stagiaire.stage.soutenance = $('#soutenance_'+ index).hasClass('green');
        stagiaire.stage.sondageWeb = $('#sondage-web_'+ index).hasClass('green');
        stagiaire.stage.ficheVisite = $('#fiche-visite_'+ index).hasClass('green');
        stagiaire.stage.visiteFaite = $('#visite-faite_'+ index).hasClass('green');
        stagiaire.stage.renduRapport = $('#rendu-rapport_'+ index).hasClass('green');
        stagiaire.stage.visitePlanifiee = $('#visite-planifiee_'+ index).hasClass('green');
        stagiaire.stage.cahierDesCharges = $('#cahier-des-charges_'+ index).hasClass('green');
        stagiaire.stage.ficheEvaluationEntreprise = $('#fiche-evaluation-entreprise').hasClass('green');

        stagiaire.promo = {};
        stagiaire.stage.entreprise = {};
        stagiaire.promo.id = $('#promo_'+ index).val();
        stagiaire.stage.entreprise.id = $('#entreprise_'+ index).val();

        return stagiaire;
    }

    /**
     * fonction qui envoi une requete requestHeader contenant le token csrf
     * pour autoriser les methode ajax post
     */
    function handleCsrfToken(){
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    }

    function saveChanges(stagiaires){
        handleCsrfToken();

        $.ajax({
            url: "/etudiants/update",
            type: "POST",
            contentType : 'application/json; charset=utf-8',
            dataType : 'json',
            data: JSON.stringify(stagiaires),
            success: function(data){
                PNotify.prototype.options.styling = "fontawesome";

                var errors = JSON.parse(data.errors.chars);

                if(errors.length == 0){
                    new PNotify({
                        type : 'success',
                        title: 'Succès',
                        text: 'Les changements ont enregistrés'
                    });

                    $('.dirty').removeClass('dirty');
                } else {
                    $.each( errors, function( index, errorMessage ) {
                        new PNotify({
                            type : 'error',
                            title: 'Erreur',
                            text: errorMessage
                        });
                    });
                }
            }
        });
    }
});