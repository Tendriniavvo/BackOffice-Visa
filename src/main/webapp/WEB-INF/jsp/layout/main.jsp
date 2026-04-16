<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BackOffice Visa</title>
    <link rel="stylesheet" href="/css/app.css">
</head>
<body>
    <div class="page">
        <jsp:include page="../fragments/sidebar.jsp" />

        <div class="content-zone">
            <jsp:include page="../fragments/header.jsp" />
            <jsp:include page="../fragments/navbar.jsp" />

            <main class="main">
                <section class="card">
                    <jsp:include page="../fragments/content.jsp" />
                </section>
            </main>
        </div>
    </div>
</body>
</html>
