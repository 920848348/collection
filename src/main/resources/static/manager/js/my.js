let refreshFolders = function () {
    $.ajax({
        async: false,
        type: "POST",
        dataType: "json",
        url: "/manager/getFoldersName",
        success: function (folders) {
            let $folders = $(window.parent.document.getElementById("folders"));
            $folders.find("dd:not(:first)").remove();
            $.each(folders,function (i,folder) {
                if(folder != "未分类"){
                    $folders.append("<dd data-name=\"folder\" class=\"layui-anim layui-anim-scale\"><a lay-href=\"/manager/openFolder/"+ folder +"\">"+ folder +"</a></dd>");
                }
            });
        },
        error: function () {
            window.top.location.href = "/400";
        }
    });
}