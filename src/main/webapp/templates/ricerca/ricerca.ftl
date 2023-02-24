<#list users as u>
    <p>u ${u.getEmail()}</p>
</#list>

<#list collections as c>
    <p>c ${c.getNome()}</p>
</#list>

<#list nfts as n>
    <p>n ${n.getKey()}</p>
</#list>

<#list wallets as w>
    <p>w ${w.getKey()}</p>
</#list>
