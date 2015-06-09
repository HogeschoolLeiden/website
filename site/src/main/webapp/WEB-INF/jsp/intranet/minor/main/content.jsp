<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<% pageContext.setAttribute("newline", "\n"); %>

<div id="maincontent" >
    <article>
        <h1>${fn:escapeXml(minor.title)}</h1>
        
        <table class="minor">
            <tag:textfield data="${minor.educationalUnit}" label="Penvoerende opleiding" />            
            <tag:textfield data="${minor.ECTs}" label="Aantal ECT's" />
            <tag:textfield data="${minor.periods}" label="Onderwijsperiode(s)" />
            <tag:textfield data="${minor.moduleCode}" label="Cursuscode in Osiris" />
            <tag:textfield data="${minor.introduction}" label="Omschrijving" />           
            <tag:textfield data="${minor.goals}" label="Leerdoelen" />
            <tag:accessibility data="${minor.accessibilityInternal}" label="Toegankelijk voor" />           
            <tag:textfield data="${minor.extraInformation}" label="Extra voorwaarden van toelating" />
            <tag:longfield data="${minor.minNrOfStudents}" label="Minimum aantal studenten" hideWhenZero="${true}"/>
            <tag:longfield data="${minor.maxNrOfStudents}" label="Maximum aantal studenten" hideWhenZero="${true}"/>
            <tag:textfield data="${minor.examination}" label="Toetsing" />
            <tag:educationtypes data="${minor.educationTypes}" otherValue="${minor.educationTypeOther}" label="Type onderwijs"/>
            <tag:roster data="${minor.roster}" label="Lesdagen"/>
            <tag:textfield data="${minor.literature}" label="Literatuur" />
            <tag:valuelistitems data="${minor.languages}" label="Taal" valuelist="${talen}"/>
            <tag:textfield data="${minor.involvedEducations}" label="Betrokken opleidingen" />
            <tag:textfield data="${minor.teachers}" label="Docenten" />
            <tag:contactperson data="${minor.contactPerson}" label="Contactpersoon voor de minor" />
        </table>
        
    </article>
</div>

<%@include file="/WEB-INF/jsp/common/metadata.jsp" %> 