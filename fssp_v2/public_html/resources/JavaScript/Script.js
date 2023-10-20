function startLoader()
{
 PF('loader').show();
}

function openCapcha()
{
 var captcha;
 captcha=document.getElementById('captcha');
 captcha.style.display='block';
}

async function readClipboard() 
{
  try 
   {
    var text = await navigator.clipboard.readText();
    var input = document.getElementById("form2:fio");
    if(text !== '')
    input.value = text; 
   } 
   catch (err) {
    alert('Ошибка чтения из буфера обмена');
   }
}

function fsspLoader_start()
{
 var loader;
 loader=document.getElementById('fsspLoader');
 loader.style.display='block';
}

function fsspLoader_stop()
{
 var loader;
 loader=document.getElementById('fsspLoader');
 loader.style.display='none';
}

function fmsLoader_start()
{
 var loader;
 loader=document.getElementById('fmsLoader');
 loader.style.display='block';
}

function fmsLoader_stop()
{
 var loader;
 loader=document.getElementById('fmsLoader');
 loader.style.display='none';
}

function updateFMS(value)
{
 var resultFMS;
 resultFMS=document.getElementById('displayFms');
 resultFMS.value=value;
}
