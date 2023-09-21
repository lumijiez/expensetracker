<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Department List</title>
</head>
<body>
    <h1>Department List</h1>
    <table>
        <thead>
            <tr>
                <th>Department ID</th>
                <th>Name</th>
                <th>Group Name</th>
                <th>Modified Date</th>
            </tr>
        </thead>
        <tbody>
            <tr th:each="department : ${basic}">
                <td th:text="${basic.actorName}"></td>
            </tr>
        </tbody>
    </table>
</body>
</html>
