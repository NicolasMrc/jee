/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    $(function() {
        $("input:file").change(function (){
            var fileName = $(this).val().replace(/C:\\fakepath\\/i, '');
            $(".filename").text(fileName);
            $(".validate").removeClass('disabled');
            $(".validate").removeClass('btn-secondary');
            $(".validate").addClass('btn-success');
        });
    });
});
