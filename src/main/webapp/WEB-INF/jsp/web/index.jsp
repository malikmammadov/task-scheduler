<%--
  Created by IntelliJ IDEA.
  User: malik
  Date: 11/10/19
  Time: 1:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Task Scheduler</title>


    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
<%--    <script src="https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap.min.js"></script>--%>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css"/>

    <style>
        body {
            margin: 0;
            padding: 0;
            background-color: #f1f1f1;
        }

        .box {
            width: 1270px;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-top: 25px;
            box-sizing: border-box;
        }
    </style>
</head>
<body>


<div class="container box">
    <div>
        <a href="add" class="btn btn-primary">Add Task</a>
    </div>
    <br>
    <div class="table-responsive">
        <table id="task_table" class="table table-bordered table-striped">
            <thead>
            <tr>
                <td>ID</td>
                <td>Task Name</td>
                <td>Description</td>
                <td>Start Date</td>
                <td>End Date</td>
                <td>Person</td>
                <td>Insert date</td>
                <td>Last Update</td>
                <td>Action</td>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>

<%--Popup window task view start--%>
<div class="modal fade" id="popupview" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Task</h4>
            </div>
            <div class="modal-body">
                <div class="modal-body" style="font-size:  larger">
                    Task Name : <span style="font-size: large" id="taskName"></span> <br>
                    Description :<span style="font-size: large" id="description"></span> <br>
                    Start Date :<span style="font-size: large" id="startDate"></span> <br>
                    End Date :<span style="font-size: large" id="endDate"></span> <br>
                    Person :<span style="font-size: large" id="person"></span> <br>
                    Insert date :<span style="font-size: large" id="insertDate"></span> <br>
                    Last Update :<span style="font-size: large" id="lastUpdate"></span> <br>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<%--Popup window task view end--%>
</body>
<script>
    $(document).ready(function () {

        fetch_data();

        function fetch_data() {
            $('#task_table').DataTable({
                "lengthMenu": [[5, 10, 20, 50, 100, -1], [5, 10, 20, 50, 100, "All"]],
                "processing": true,
                "serverSide": true,
                "ajax": "/list"
            });
        }

        $(document).on('click', '.delete', function () {
            var id = $(this).attr("id");
            if (confirm("are you sure you want to remove task?")) {
                $.ajax({
                    url: "delete",
                    method: "post",
                    data: {id: id},
                    success: function (data) {
                        $('#alert_message').html('<div class="alert alert-success">' + data + '</div>');
                        $('#task_table').DataTable().destroy();
                        fetch_data();
                    }
                });
                setInterval(function () {
                    $('#alert_message').html('');
                }, 100);
            }
        });
    });

    function showdetail(button) {
        var id = button.id;
        $.ajax({
            url: "view",
            method: "get",
            data: {id: id},
            success: function (serverResponse) {
                document.getElementById("taskName").innerHTML = serverResponse.taskName;
                document.getElementById("description").innerHTML = serverResponse.description;
                document.getElementById("startDate").innerHTML = serverResponse.startDate;
                document.getElementById("endDate").innerHTML = serverResponse.endDate;
                document.getElementById("person").innerHTML = serverResponse.person;
                document.getElementById("insertDate").innerHTML = serverResponse.insertDate;
                document.getElementById("lastUpdate").innerHTML = serverResponse.lastUpdate;
            }
        });
    }
</script>

</html>