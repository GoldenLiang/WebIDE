<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>OnLine JAVA IDE</title>
</head>
<script type="text/javascript" src="/webjars/jquery/1.11.3/jquery.js"></script>
<script type="text/javascript" src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/3.3.7/css/bootstrap.css">
<body>
<ol class="breadcrumb">
    <li><a href="#">Home</a></li>
    <li><a href="#">JAVA</a></li>
    <li class="active">IDE</li>
</ol>
<form role="form">
    <div class="form-group" style="padding: 20px">
        <label for="name">编码区</label>
        <textarea id="inputArea" class="form-control" rows="15">public class Main {
                public static void main(String[] args){
                    System.out.println("hello world!");
            }
        }
        </textarea>
    </div>
    <button onclick="doSubmit()" type="button" style="width: 200px" class="btn btn-success center-block"
            aria-hidden="true">提交
    </button>
    <br><br><br>
    <div style="padding: 20px">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">运行结果</h3>
            </div>
            <div class="panel-body" id="resultDiv">

            </div>
        </div>
    </div>
</form>
</body>
<script>
    function doSubmit() {
        var url = "/complie";
        var data = {"javaSource": $("#inputArea").val()};
        $.post(url, data, function (result) {
            alert("结果:" + result);
            $("#resultDiv").html(result);
        });
    }
</script>
</html>