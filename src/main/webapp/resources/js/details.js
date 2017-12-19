

$(document).ready(function(){

    //TODO
    //Json object containing only the field changed by the user to update only necessary field
    var jsonUpdate = {};
    jsonUpdate.stage = {};
    jsonUpdate.stage.id = $('#id-stage').text();
    jsonUpdate.id = $('#id-etudiant').text();
    jsonUpdate.promo = {};
    jsonUpdate.stage.entreprise = {};


    /**
     * switch la valeur des boutons booleans représentant les champs binaires de l'etudiant
     */
    $(".btn-bool").click(function(){
        if($(this).hasClass("badge-danger")){
            $(this).removeClass("badge-danger");
            $(this).addClass("badge-success");
            $(this).children('i').removeClass('fa-times');
            $(this).children('i').addClass('fa-check');
            $(this).children('p').text('true');
        } else {
            $(this).removeClass("badge-success");
            $(this).addClass("badge-danger");
            $(this).children('i').removeClass('fa-check');
            $(this).children('i').addClass('fa-times');
            $(this).children('p').text('false');
        }
    });

    /**
     * permet d'ajouter en ajax une nouvelle entreprise dans la base de donnée
     */
    $('#addEntreprise').click(function(){
        var nom = $('#nom-entreprise').val();
        var adresse = $('#adresse-entreprise').val();

        handleCsrfToken();

        var entreprise = {nom: nom, adresse : adresse};

        $.ajax({
            url: "/entreprise/new",
            type: "POST",
            contentType : 'application/json; charset=utf-8',
            dataType : 'json',
            data: JSON.stringify(entreprise),
            success: function(data){
                PNotify.prototype.options.styling = "fontawesome";

                var errors = JSON.parse(data.errors.chars);

                if(errors.length == 0){
                    var addedEntreprise = JSON.parse(data.entreprise.chars);
                    new PNotify({
                        type : 'success',
                        title: 'Succès',
                        text: data.message.chars
                    });
                    $('#entreprise').append($('<option>', {
                        value: addedEntreprise.id,
                        text: addedEntreprise.nom
                    }));
                    $('#entrepriseModal').modal('hide');
                    $('#nom-entreprise').val("");
                    $('#adresse-entreprise').val("");
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
    });

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

    /**
     * sauvegarde des changement sur l'utilisateur en ajax
     */
    $('#btnSave').click(function(){
        jsonUpdate.stage.noteTech = $('#note-tech').val();
        jsonUpdate.stage.noteCom = $('#note-com').val();
        jsonUpdate.stage.maitreDeStage = $('#maitre-de-stage').val();
        jsonUpdate.stage.fin = $('#fin').val();
        jsonUpdate.stage.debut = $('#debut').val();
        jsonUpdate.stage.soutenance = $('#soutenance').text();
        jsonUpdate.stage.sondageWeb = $('#sondageWeb').text();
        jsonUpdate.stage.commentaire = $('#commentaire').val();
        jsonUpdate.stage.ficheVisite = $('#ficheVisite').text();
        jsonUpdate.stage.visiteFaite = $('#visiteFaite').text();
        jsonUpdate.stage.renduRapport = $('#renduRapport').text();
        jsonUpdate.stage.visitePlanifiee = $('#visitePlanifiee').text();
        jsonUpdate.stage.cahierDesCharges = $('#cahierDesCharges').text();
        jsonUpdate.stage.descriptionMission = $('#description-mission').val();
        jsonUpdate.stage.ficheEvaluationEntreprise = $('#ficheEvaluationEntreprise').text();

        jsonUpdate.promo.id = $('#promo').val();
        jsonUpdate.stage.entreprise.id = $('#entreprise').val();

        handleCsrfToken();

        $.ajax({
            url: "/etudiant/update",
            type: "POST",
            contentType : 'application/json; charset=utf-8',
            dataType : 'json',
            data: JSON.stringify(jsonUpdate),
            success: function(data){
                PNotify.prototype.options.styling = "fontawesome";

                var errors = JSON.parse(data.errors.chars);

                if(errors.length == 0){
                    var etudiant = JSON.parse(data.etudiant.chars);;
                    new PNotify({
                        type : 'success',
                        title: 'Succès',
                        text: data.message.chars
                    });

                    $('.title').text('Details de ' + etudiant.prenom + ' ' + etudiant.nom);
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
    });
});