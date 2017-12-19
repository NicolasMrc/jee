

$(document).ready(function(){

    /**
     * permet d'ajouter en ajax une nouvelle entreprise dans la base de donnée
     */
    $('#generatePdf').click(function(){

        handleCsrfToken();

        var maitreDeStage = $("#maitre-de-stage").val();
        var idEtudiant = $("#idEtudiant").text();

        $.ajax({
            url: "generate",
            type: "POST",
            data: {maitreDeStage : maitreDeStage, idEtudiant : idEtudiant},
            success: function(data){
                console.log(data);
                window.open('/resources/fiches/' + data.filename.chars, '_blank');
            },
            error: function (error) {
                console.log(error);
            }
        });
    });


    $('#generateDraft').click(function(){
        handleCsrfToken();

        var maitreDeStage = $("#maitre-de-stage").val();
        var idEtudiant = $("#idEtudiant").text();

        $.ajax({
            url: "generateDraft",
            type: "POST",
            data: {maitreDeStage : maitreDeStage, idEtudiant : idEtudiant},
            success: function(data){
                console.log(data);
                window.open('/resources/fiches/' + data.filename.chars, '_blank');
            },
            error: function (error) {
                console.log(error);
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

});