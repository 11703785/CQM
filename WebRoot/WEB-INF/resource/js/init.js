var appDicMap;
var appDicKey = {
    cerType: '1',
    archiveType: '2',
    archiveStatus: '3',
    queryReason: '7',
    queryFormat: '8',
    queryType: '9',
    queryBatchFlag: '10',
    querySource: '11',
    resultStatus: '14',
    queryStatus: '15',
    checkStatus: '16',
    orgStatus: '30',
    userStatus: '31',
    credUserLevel: '32',
    credUserType: '33',
    credUserStatus: '34',
    credUserCategory: '39',
    userType: '35',
    roleStatus: '36',
    roleType: '37',
    orgCorp: '38',
    ecQueryType: '50',
    ecQueryFormat: '51',
    ecQueryReason: '52',
    ecQueryStatus: '54',
    alarmType: '944',
    profileType:'947',
    filesType:'950',
};
$.ajax({
    url: 'index/navigation',
    dataType: "JSON",
    async: false,
    cache: false,
    success: function (resp) {
        var menu = "";
        var mini_menu = "";
        $.each(
            resp,
            function (n, value) {
                var id = value.id;
                var text = value.text;
                menu = menu
                    + "<div title=' "
                    + value.text
                    + "' data-options=\"iconCls:'" + value.iconCls + "'\" style=\"overflow:auto;padding:10px;background-color:#FCFCFC;\"><ul class='easyui-tree'>";
                $.each(
                    value.children,
                    function (n, value) {
	                        menu = menu
	                            + "<li><i class='" + value.iconCls + "'></i><a href='#' onclick='$.insdep.control(\""
	                            + value.href
	                            + "\",\"\",\"<b>"
	                            + value.text
	                            + "</b><span>"
	                            + text
	                            + "</span>\"";
	                        if (value.iframe === true) {
	                            menu = menu +",true";
	                        }
	                        menu = menu + ")'> "
	                            + value.text
	                            + "</a></li>";
                    });
                menu = menu + "</ul></div>";
                /*$("#rjhcmean_mini").append(
                    "<li><i class='" + value.iconCls + "'></i><p>"
                    + value.text + "</p></li>");*/

            });
        $("#rjhcmean").append(menu);

    },
    error: function (XMLHttpRequest, textStatus, errorThrown) {
        $.messager.alert("", textStatus || errorThrown, "error");
    }
});