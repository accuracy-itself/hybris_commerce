<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="questions-content">
    WELCOME TO QUESTIONSSSSSS!!!!!!
    <br/>
    <c:forEach var="question" items="${product.questions}" begin="0" end="${numberOfQuestionsToShow - 1}" varStatus="status" >
        <div style="font-size: ${fontSize}px">
        ${question.questionCustomer}: ${question.question} <br/>
        ${question.answerCustomer}: ${question.answer}
        </div>
        <br/>
    </c:forEach>
</div>