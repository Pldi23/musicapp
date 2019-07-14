<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="demo">

    <h2>The XMLHttpRequest Object</h2>

    <button type="button" onclick="loadDoc('../ajax_info.txt', myFunction)">Change Content
    </button>
</div>

<script>
    function loadDoc(url, cFunction) {
        var xhttp;
        xhttp=new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                cFunction(this);
            }
        };
        xhttp.open("GET", url, true);
        xhttp.send();
    }
    function myFunction(xhttp) {
        document.getElementById("demo").innerHTML =
            xhttp.responseText;
    }
</script>
</body>
<%--<script src="../loadDoc.js"></script>--%>
</html>
