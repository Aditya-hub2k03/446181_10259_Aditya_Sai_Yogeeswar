<%@ include file="../common/header.jsp" %>
<h3>Theatres for ${movie.title}</h3>

<c:forEach var="s" items="${shows}">
    <div>
        <p>Theatre ID: ${s.theatreId}</p>
        <p>Show Time: ${s.showTime}</p>
        <p>Price: ₹${s.price}</p>
        <a href="book?showId=${s.showId}">Select Show</a>
    </div>
    <hr>
</c:forEach>

<%@ include file="../common/footer.jsp" %>
