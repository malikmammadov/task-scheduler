<%--
  Created by IntelliJ IDEA.
  User: malik
  Date: 11/10/19
  Time: 3:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create Task</title>
    <link rel="stylesheet" type="text/css" href="view.css" media="all">
    <script type="text/javascript" src="view.js"></script>
</head>

<body id="main_body">
<img id="top" src="top.png" alt="">
<div id="form_container">
    <h1>Create Task</h1>
    <form:form
            modelAttribute="task"
            id="create_task_form"
            class="appnitro"
            method="post"
            action="create">
        <div class="form_description">
            <h2>Create Task</h2>
        </div>
        <ul>

            <li id="li_1">
                <label class="description" for="task_name">Task Name </label>
                <div>
                    <form:input
                            path="taskName"
                            type="text"
                            id="task_name"
                            name="task_name"
                            class="element text medium"/>
                </div>
                <span id="task_name_error" style="display: none; color: red;"></span>
                <form:errors cssStyle="color: red" path="taskName"/>
            </li>

            <li id="li_2">
                <label class="description" for="description">Description </label>
                <div>
                    <form:textarea
                            path="description"
                            id="description"
                            name="description"
                            class="element textarea medium"/>
                </div>
                <form:errors cssStyle="color: red" path="description"/>
            </li>

            <li id="li_3">
                <label class="description" for="start_date">Start Date </label>
                <div>
                    <form:input
                            path="startDate"
                            type="date"
                            id="start_date"
                            name="start_date"
                            class="element text"/>
                </div>
                <form:errors cssStyle="color:red;" path="startDate"/>
            </li>


            <li id="li_4">
                <label class="description" for="end_date">End Date </label>
                <div>
                    <form:input
                            path="endDate"
                            type="date"
                            id="end_date"
                            name="end_date"
                            class="element text"/>
                </div>
                <form:errors cssStyle="color: red" path="endDate"/>
            </li>

            <li id="li_5">
                <label class="description" for="person">Person </label>
                <div>
                    <form:input
                            path="person"
                            id="person"
                            name="person"
                            class="element text medium"
                            type="text"/>
                </div>
                <form:errors cssStyle="color: red" path="person"/>
            </li>

            <li class="buttons">
                <input id="saveForm" class="button_text" type="submit" name="submit" value="Submit"/>
            </li>

        </ul>
    </form:form>
</div>
<img id="bottom" src="bottom.png" alt="">
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
    $(document).ready(function () {

        function validateTaskName(value) {
            var valid = true;
            $.ajax({
                method: 'GET',
                url: getBaseUrl() + 'checkTaskName',
                data: {'taskName': value},
                success: function (response) {
                    $('#task_name_error').text('task name already exist');
                    $('#task_name_error').show();
                    valid = false;
                }
            });
            if (valid) {
                $('#task_name_error').hide();
            }
        }

        $('#task_name').on('blur', function () {
            validateTaskName($('#task_name').val());
        });

        function getBaseUrl() {
            var re = new RegExp(/^.*\//);
            return re.exec(window.location.href);
        }
    });

</script>
</html>
