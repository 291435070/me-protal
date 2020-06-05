// $(document).ready(function () {
$("#mnavh").click(function () {
    $("#starlist").toggle();
    $("#mnavh").toggleClass("open")
});

var new_scroll_position = 0;
var last_scroll_position;
var header = $("#header");
window.addEventListener("scroll", function (e) {
    last_scroll_position = window.scrollY;
    if (new_scroll_position < last_scroll_position && last_scroll_position > 80) {
        header.removeClass("slideDown");
        header.addClass("slideUp")
    } else {
        if (new_scroll_position > last_scroll_position) {
            header.removeClass("slideUp");
            header.addClass("slideDown")
        }
    }
    new_scroll_position = last_scroll_position
});

var offset = 300, offset_opacity = 1200, scroll_top_duration = 700, $back_to_top = $(".cd-top");
$(window).scroll(function () {
    ($(this).scrollTop() > offset) ? $back_to_top.addClass("cd-is-visible") : $back_to_top.removeClass("cd-is-visible cd-fade-out");
    if ($(this).scrollTop() > offset_opacity) {
        $back_to_top.addClass("cd-fade-out")
    }
});

$back_to_top.on("click", function (event) {
    event.preventDefault();
    $("body,html").animate({scrollTop: 0,}, scroll_top_duration)
});

//¹Ì¶¨²àÀ¸
new hcSticky("aside", {stickTo: "main", innerTop: 200, followScroll: false})

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURI(r[2]);
    }
    return null;
}

var option = getUrlParam('type');
console.log('type:' + option);
$('#starlist a').removeClass("active");
$('#starlist a').eq(option).addClass("active");

//Êý×Ö¹ö¶¯
new CountUp('count1', 0, 71, 0, 2, {useGrouping: false}).start();
new CountUp('count2', 0, 20, 0, 2, {useGrouping: false}).start();
new CountUp('count3', 0, 520, 0, 2, {useGrouping: false}).start();
new CountUp('count4', 0, 31, 0, 2, {useGrouping: false}).start();
// });