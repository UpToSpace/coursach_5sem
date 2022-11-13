$(window).on('load', function () {
    var $div2 = $('#div2');

    $div2.replaceWith( '<div id="div2">'+ $div2.html() +'</div>');
});