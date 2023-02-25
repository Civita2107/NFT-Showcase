<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!--TAILWIND-->
    <script src="https://cdn.tailwindcss.com"></script>
    <!--STYLE-->
    <link rel="stylesheet" href="${assets}/style.css">
    <link rel="icon" href="${assets}/favicon.ico">
    <title>NFT Showcase</title>
</head>
<body>

<#include "outline_navbar.ftl">

<div class="w-full min-h-screen p-2 h-fit">
    <#include content_tpl>
</div>

<#include "outline_footer.ftl">

</body>
             <script>
  const filtro = document.getElementById('filtro');
  const utenti = document.getElementById('utenti');
  const nft = document.getElementById('nft');
  const collezioni = document.getElementById('collezioni');


  
  filtro.addEventListener('change', () => {
    if (filtro.value === 'utenti') {
      utenti.classList.remove('hidden');
      nft.classList.add('hidden');
      collezioni.classList.add('hidden');
    } else if (filtro.value === 'nft') {
      utenti.classList.add('hidden');
      nft.classList.remove('hidden');
      collezioni.classList.add('hidden');
    } else if (filtro.value === 'collezioni') {
      utenti.classList.add('hidden');
      nft.classList.add('hidden');
      collezioni.classList.remove('hidden');
    } else{    
      utenti.classList.remove('hidden');
      nft.classList.remove('hidden');
      collezioni.classList.remove('hidden');}
  });
</script>
</html>