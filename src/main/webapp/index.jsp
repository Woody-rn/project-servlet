<%@ page import="com.tictactoe.Sign" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Tic-Tac-Toe</title>
    <link href="static/main.css" rel="stylesheet">
</head>
<body>
<h1>
    Tic-Tac-Toe
    <span class="smart">smart</span>
</h1>

<table>
    <tr>
        <td id='0' onclick="window.location='/logic?click=0'">${sessionScope.data.get(0).getSign()}</td>
        <td id='1' onclick="window.location='/logic?click=1'">${sessionScope.data.get(1).getSign()}</td>
        <td id='2' onclick="window.location='/logic?click=2'">${sessionScope.data.get(2).getSign()}</td>
    </tr>
    <tr>
        <td id='3' onclick="window.location='/logic?click=3'">${sessionScope.data.get(3).getSign()}</td>
        <td id='4' onclick="window.location='/logic?click=4'">${sessionScope.data.get(4).getSign()}</td>
        <td id='5' onclick="window.location='/logic?click=5'">${sessionScope.data.get(5).getSign()}</td>
    </tr>
    <tr>
        <td id='6' onclick="window.location='/logic?click=6'">${sessionScope.data.get(6).getSign()}</td>
        <td id='7' onclick="window.location='/logic?click=7'">${sessionScope.data.get(7).getSign()}</td>
        <td id='8' onclick="window.location='/logic?click=8'">${sessionScope.data.get(8).getSign()}</td>
    </tr>
</table>

<c:set var="CROSSES" value="<%=Sign.CROSS%>"/>
<c:set var="NOUGHTS" value="<%=Sign.NOUGHT%>"/>

<c:if test="${sessionScope.winner == CROSSES}">
    <p class="game-over">CROSSES WIN!</p>
    <div class="button">
        <button onclick="restart()">Start again</button>
    </div>
</c:if>

<c:if test="${sessionScope.winner == NOUGHTS}">
    <p class="game-over">NOUGHTS WIN!</p>
    <div class="button">
        <button onclick="restart()">Start again</button>
    </div>
</c:if>

<c:if test="${sessionScope.draw}">
    <p class="game-over">IT'S A DRAW</p>
    <br>
    <div class="button">
        <button onclick="restart()">Start again</button>
    </div>
</c:if>


<script>
    async function restart() {
        const response = await fetch('/restart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            }
        });
        if (response.ok) {
            location.reload();
        }
    }

    const allTD = document.querySelectorAll('td');
    allTD.forEach(td => {
        if (td.textContent == 'X') {
            td.className = 'cross';
            td.textContent = "";

        } else if (td.textContent == '0') {
            td.className = 'circle';
            td.textContent = "";
        }
    });

    <% List<Integer> winCell = (List<Integer>) session.getAttribute("dataWinCell"); %>

    const winCell = <%=winCell != null ? winCell : "[]" %>;
    allTD.forEach(td => {
        if (winCell.includes(Number(td.id))) {
            td.classList.add('win-cell');
        }
    });
    

</script>

</body>
</html>