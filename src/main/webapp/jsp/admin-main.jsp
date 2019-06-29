<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
<form action="upload" enctype="multipart/form-data" method="post">
    <input type="hidden" name="command" value="addtrack"/>
    <h3>ADD TRACK</h3>
    <label>
        Track name :
        <input type="text" name="trackname" required="" placeholder="track name"/>
    </label>
    <br>
    <label>
        Track path :
        <input type="file" name="mediapath" accept=".mp3"/>
    </label>
    <br>
    <label>
        Singers :
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
        Genre :
        <input type="text" name="genre" required="" placeholder="genre"/>
    </label>
    <br>
    <label>
        Release date :
        <input type="date" name="releasedate"/>
    </label>
    <br>
    <br>
    <input type="submit" name="submit" value="Add track">
    <br/>
</form>
${addresult}
</body>
</html>