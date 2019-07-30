<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
    function validateDateField(fieldId) {
        $(document).ready(function () {
            var today = new Date();
            var day = today.getDate() > 9 ? today.getDate() : "0" + today.getDate(); // format should be "DD" not "D" e.g 09
            var month = (today.getMonth() + 1) > 9 ? (today.getMonth() + 1) : "0" + (today.getMonth() + 1);
            var year = today.getFullYear() - 6;
            $("#" + fieldId).attr('max', year + "-" + month + "-" + day);
        });
    }
</script>
