const favArea = document.querySelector('.fav-area');
function handleWheel(event) {
    console.log("onScroll")
    if (event.deltaY != 0) {
        event.preventDefault(); // 기본 수직 스크롤 방지
        favArea.scrollLeft += event.deltaY; // 가로 스크롤
    }
}
favArea.addEventListener("wheel",handleWheel);



/*즐겨찾기 리스트 제목 보여주는 기능*/
function FavTitle({isAuth}){
    return(
        <>
            {isAuth? 
            <div className="fav-title__title"></div> : /*위Favorites 아래Populars*/ 
            <div className="fav-title__title"></div>}
        </>
    );
}
const favTitle = document.querySelector(".fav-title");
ReactDOM.render(<FavTitle isAuth={isAuth}/>,favTitle);


const delFavTogle = document.querySelector(".fav-btn__togle-fav-del");
if(isAuth){
    let classes =  delFavTogle.classList;
    if(classes.contains("hidden")){
        delFavTogle.classList.remove("hidden");
    }
}else{
    let classes =  delFavTogle.classList;
    if(!classes.contains("hidden")){
        delFavTogle.classList.add("hidden");
    }
}
delFavTogle.addEventListener("click",function(){
    const deleteBtns = document.querySelectorAll(".fp-item__delete-btn");
    if (deleteBtns.length>0){
        deleteBtns.forEach((btn) => {
            if (btn.classList.contains("hidden")) {
                btn.classList.remove("hidden");
            } else {
                btn.classList.add("hidden");
            }
        });
    }
});

const goCoffeeListBtn = document.querySelector(".fav-btn__go-coffeelist");
goCoffeeListBtn.addEventListener("click",function(){
    window.location.href = "/coffeeList.do"
})