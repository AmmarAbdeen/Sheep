(function(){window.onload=function(){window.setTimeout(fadeout,500);}
function fadeout(){}
window.onscroll=function(){
    var header_navbar=document.querySelector(".navbar-area");
    if(header_navbar != null){
    var sticky=header_navbar.offsetTop;
    if(window.pageYOffset>sticky){
        header_navbar.classList.add("sticky");
    }else{
        header_navbar.classList.remove("sticky");
    }}
var backToTo=document.querySelector(".scroll-top");
if(backToTo !=null){
if(document.body.scrollTop>50||document.documentElement.scrollTop>50)
    {backToTo.style.display="flex";
}else{backToTo.style.display="none";}}
};var pageLink=document.querySelectorAll('.page-scroll');pageLink.forEach(elem=>{elem.addEventListener('click',e=>{e.preventDefault();document.querySelector(elem.getAttribute('href')).scrollIntoView({behavior:'smooth',offsetTop:1-60,});});});let navbarToggler=document.querySelector(".navbar-toggler");var navbarCollapse=document.querySelector(".navbar-collapse");document.querySelectorAll(".page-scroll").forEach(e=>e.addEventListener("click",()=>{navbarToggler.classList.remove("active");navbarCollapse.classList.remove('show')}));
})();