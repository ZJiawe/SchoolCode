$(document).ready(function(){

--- jQuery functions go here ----

});var data = [{
    text: "北国冰封，千里冰封，万里雪飘"
},
{
    text: "待从头收拾旧山河，朝天阙"
},
{
    text: "但愿人长久千里共婵娟"
},
{
    text: "才下船头却上心头"
}];
//从后台取数据
//$.ajax({
//    url: "http://localhost/Ehcache/baraage",//服务器端应改为服务器端的地址
//    type: "post",
//    async: false,
//    success: function(st) {
//       // console.log(st);																																						
//        $.extend(true, data, st); //st覆盖data
//       // console.log(data);
//    }
//});
var num = data.length;
for (var i = 0; i < num; i++) {
    $("ul").append("<li><div class='BarMes'>" + data[i].text + "</div></li>");
    //console.log(data[i].text);
}
//
//function add() {
//    $.ajax({
//        url: "****",
//        type: "post",
//        async: false,
//        success: function(st) {
//            //console.log(st);
//            $.extend(true, data, st);
//            //console.log(data);
//        }
//    });
//    var num = data.length;
//    for (var i = 0; i < num; i++) {
//        $("ul").append("<li><div class='BarMes'>" + data[i].text + "</div></li>");
//        //console.log(data[i].text);
//    }
//
//}