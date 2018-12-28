(function ($) {

    $.fn.areaDraw = function (options) {
        $(this).append('<canvas id="main_zone" width="800" height="500" style="background-color:#fff;"></canvas>');
        return document.getElementById("main_zone");
    }

})(jQuery);