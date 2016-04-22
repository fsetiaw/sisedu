<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-GB">
<head>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
<script>
$(document).ready(function() {
    function close_accordion_section() {
        $('.accordion .accordion-section-title').removeClass('active');
        $('.accordion .accordion-section-content').slideUp(300).removeClass('open');
    }
 
    $('.accordion-section-title').click(function(e) {
        // Grab current anchor value
        var currentAttrValue = $(this).attr('href');
 
        if($(e.target).is('.active')) {
            close_accordion_section();
        }else {
            close_accordion_section();
 
            // Add active class to section title
            $(this).addClass('active');
            // Open up the hidden content panel
            $('.accordion ' + currentAttrValue).slideDown(300).addClass('open'); 
        }
 
        e.preventDefault();
    });
});
</script>
<style>
/*----- Accordion -----*/
.accordion, .accordion * {
    -webkit-box-sizing:border-box; 
    -moz-box-sizing:border-box; 
    box-sizing:border-box;
}
 
.accordion {
    overflow:hidden;
    box-shadow:0px 1px 3px rgba(0,0,0,0.25);
    border-radius:3px;
    background:#f7f7f7;
}
 
/*----- Section Titles -----*/
.accordion-section-title {
    width:100%;
    padding:15px;
    display:inline-block;
    border-bottom:1px solid #1a1a1a;
    background:#333;
    transition:all linear 0.15s;
    /* Type */
    font-size:1.200em;
    text-shadow:0px 1px 0px #1a1a1a;
    color:#fff;
}
 
.accordion-section-title.active, .accordion-section-title:hover {
    background:#4c4c4c;
    /* Type */
    text-decoration:none;
}
 
.accordion-section:last-child .accordion-section-title {
    border-bottom:none;
}
 
/*----- Section Content -----*/
.accordion-section-content {
    padding:15px;
    display:none;
}
</style>
</head>
<body>
<div class="accordion">
    <div class="accordion-section">
        <a class="accordion-section-title" href="#accordion-1">Accordion Section #1</a>
         
        <div id="accordion-1" class="accordion-section-content">
            <p>Mauris interdum fringilla augue vitae tincidunt. Curabitur vitae tortor id eros euismod ultrices. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Praesent nulla mi, rutrum ut feugiat at, vestibulum ut neque? Cras tincidunt enim vel aliquet facilisis. Duis congue ullamcorper vehicula. Proin nunc lacus, semper sit amet elit sit amet, aliquet pulvinar erat. Nunc pretium quis sapien eu rhoncus. Suspendisse ornare gravida mi, et placerat tellus tempor vitae.</p>
        </div><!--end .accordion-section-content-->
    </div><!--end .accordion-section-->
</div><!--end .accordion-->
</body>
</html>
