

$(document).ready(function(){
    $("input[name='selectedEtudiant']").change(function(){
        var $btn = $('#assign');
        $btn.removeClass("disabled");
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


    $("#assign").click(function(){
        var idEtudiant = $("input[name='selectedEtudiant']:checked").val();
        if(idEtudiant != null){

            handleCsrfToken();

        $.post( "/etudiant/assigner", {idEtudiant: idEtudiant})
            .done(function( data ) {
                PNotify.prototype.options.styling = "fontawesome";
                if(data.error.chars == ""){
                    new PNotify({
                        type : 'success',
                        title: 'Succès',
                        text: data.message.chars
                    });
                    var currentUserName = $('#currentUserName').text();
                    $("input[name='selectedEtudiant']:checked").parents("tr").children('#tuteur').children("p").text(currentUserName);
                } else {
                    new PNotify({
                        type : 'error',
                        title: 'Erreur',
                        text: data.error.chars
                    });
                }
            });
        }
    })
});