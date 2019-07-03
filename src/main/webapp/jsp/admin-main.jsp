<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>Welcome</title></head>
<body>
<h3>Welcome</h3>
<hr/>
Admin ${adminFirstName}, hello!
Your role: ${role}
<hr/>
<a href="controller?command=logout">Logout</a>
<form action="controller" method="post">
    <input type="hidden" name="command" value="search"/>
    <label>
        Search panel
        <input type="text" name="searchrequest" placeholder="playlist, track, musician"/>
    </label>
    <input type="submit" name="submit" value="Search">
</form>
${violations}
<form action="controller" enctype="multipart/form-data" method="post">
    <input type="hidden" name="command" value="upload"/>
    <h3>ADD TRACK</h3>
    <label>
        Track path * :
        <input type="file" name="mediapath" accept=".mp3"/>
    </label>
    <br>
    <label>
        Track name * :
        <input type="text" name="trackname" required="" placeholder="track name"/>
    </label>
    <br>
    <label>
        Singers * :
        <input type="text" name="singer" required="" placeholder="singer1"/>
        <input type="text" name="singer" placeholder="singer2"/>
        <input type="text" name="singer" placeholder="singer3"/>
        <input type="text" name="singer" placeholder="singer4"/>
        <input type="text" name="singer" placeholder="singer5"/>
        <input type="text" name="singer" placeholder="singer6"/>
    </label>
    <br>
    <label>
        Authors :
        <input type="text" name="author" placeholder="author1"/>
        <input type="text" name="author" placeholder="author2"/>
        <input type="text" name="author" placeholder="author3"/>
        <input type="text" name="author" placeholder="author4"/>
        <input type="text" name="author" placeholder="author5"/>
        <input type="text" name="author" placeholder="author6"/>
    </label>
    <br>
    <label>
        Genre * :
        <select name="genre">
            <option value="pop">Pop</option>
            <option value="rock">Rock</option>
            <option value="blues">Blues</option>
            <option value="country">Country</option>
            <option value="electronic">Electronic</option>
            <option value="folk">Folk</option>
            <option value="hip-hop">Hip-Hop</option>
            <option value="latin">Latin</option>
            <option value="r&b">R&B</option>
            <option value="soul">Soul</option>
            <option value="instrumental">Instrumental</option>
            <option value="lounge">Lounge</option>
            <option value="disco">Disco</option>
            <option value="chanson">Chanson</option>
            <option value="retro">Retro</option>
            <option value="funk">Funk</option>
        </select>
    </label>
    <br>
    <label>
        Release date * :
        <input type="date" name="releasedate"/>
    </label>
    <br>
    <br>
    <input type="submit" name="submit" value="Add track">
    <br/>
</form>
${addresult}

<form action="controller" method="post">
    <input type="hidden" name="command" value="query"/>
    <h3>Remove track</h3>
    <label>
        search track
        <input type="text" name="searchrequest" required="" placeholder="track name"/>
    </label>
    <input type="submit" name="submit" value="query">
</form>
<br>
<table>
Tracks :
    <c:forEach var="track" items="${tracks}" varStatus="status">
        <tr>
            <td><c:out value="${ track.id }"/></td>
            <td><c:out value="${ track.name }"/></td>
            <td><c:out value="${ track.singers }"/></td>
            <td><c:out value="${ track.genre }"/></td>
            <td><audio controls><source src="music/${track.uuid}" type="audio/mpeg"></audio></td>
            <td>
                <form method="get" action="controller">
                    <input type="hidden" name="command" value="remove">
                    <input type="hidden" name="uuid" value="${ track.uuid }">
                    <input type="submit" name="submit" value="remove">
                        ${removeresult}
                </form> </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>