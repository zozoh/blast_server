(function($){
    $(document.body).ready(function(){
        //-----------------------------------------------------
        $("#dg_do").click(function(){
            // Get the number to send
            var n = $("#dg_nb").val() * 1;
            // the log obj...
            var jLog = $("#log");

            // do send...
            $.get("/mock/blast?nb=" + n).done(function(re){
                jLog.text(">>>>" + n + "\n" + re + "\n" + jLog.text());
                $("#dg_nb").val(n + 1);
            });
        });
        //-----------------------------------------------------
        $("#cleanlog").click(function(){
            $("#log").text('');
        });
        //-----------------------------------------------------
        $("#decay").click(function(){
            $.get("/api/decay").done(function(re){
                $("#log").text(re + "\n" + $("#log").text());
            });
        });
        //-----------------------------------------------------
        $("#dr_do").click(function(){
            var param = eval('(' + $("#dr textarea").val() + ')');
            var jLog = $("#log");
            $.post("/mock/reblast",param).done(function(re){
                var list = (eval('(' + re + ')').data);
                for(var i=0;i<list.length;i++){
                    var o = list[i];
                    jLog.text(o.blastId 
                              + " @ " + o.location 
                              + " by : " + o.owner 
                              + "\n" 
                              + jLog.text());
                }
            }); 
        });
        //-----------------------------------------------------
    });
})(window.jQuery);