<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html><head><title>JSP Timing</title></head>
 <body>
 <h5>Счетчик времени от запуска приложения до нажатия кнопки</h5>
<jsp:useBean id="calendar" class="java.util.GregorianCalendar"/>
 <form name="Simple" action="timeaction" method="POST">
<input type="hidden" name="time" value="${calendar.timeInMillis}"/>
<input type="submit" name="button" value="Посчитать время"/>
 </form>
</body></html>
