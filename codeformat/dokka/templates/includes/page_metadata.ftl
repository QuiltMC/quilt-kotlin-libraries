<#macro display>
    <title>${pageName}</title>
    <@template_cmd name="pathToRoot">
    <link href="${pathToRoot}images/qkl-icon.png" rel="icon" type="image/png">
    </@template_cmd>
    <meta property="og:title" content="${pageName}"/>
    <meta property="og:type" content="website"/>
    <@template_cmd name="projectName">
    <meta property="og:site_name" content="${projectName}"/>
    </@template_cmd>
</#macro>