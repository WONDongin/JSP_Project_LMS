<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	alert("${msg}")
	location.href="${url}";

    var shouldClose = "${close}" === "true";
    var url = "${url}";

    if (shouldClose) {
        window.close();
    } else if (url) {
        location.href = url;
    }
</script>