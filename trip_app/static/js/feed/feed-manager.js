let timeLine = document.getElementsByTagName('ul')[0];


function getTimeLine() {
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            for (let i = 0; i < data.length; i++) {
                temp = data[i];

                timeLine.innerHTML = getFeedHtml(temp.tag_name, temp.name,
                     temp.img_path, temp.content, temp.likes,
                     temp.upload_date);

            }
        }
    }
    xhr.open('post', '/time-line', true);
    xhr.send();
}

function getMyFeed() {
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            if (xhr.responseText === 'no-data') {
                alert('아직 게시물이 없습니다.');
            } else {
                console.log(JSON.parse(xhr.responseText));
            }
        }
    }
    xhr.open('post', '/feeds', true);
    xhr.send();
}

function getFeedHtml(tag, author, img_path, content, likes, date) {
    let feedHtmlStart =
        `<li class="feed">
        <div class="tag">
            <i class="fas fa-tags"></i>${tag}
        </div>
        <div class="card post">
            <div class="author">
                  <i class="far fa-user-circle mr-1"></i>
                  <b class="name">${author}</b>
            </div>
         
<div style="overflow-y: scroll; height: 400px" >`

    let feedHtmlEnd  =
        `</div>  <p class="content">
            ${content}
        </p>
        <div class="desc">
            <button class="like mr-2">
                <i class="far fa-heart mr-2"></i>
                ${likes}
                </button>
                <div class="date d-flex align-self-center">
                    <i class="fas fa-shoe-prints"></i>
                    ${date}
                </div>
               </div>
           </div>
       <div class="card comment">
                            댓글
                        </div>
    </li>`

    let feedHtmlMid = '';
    for(let i = 0; i < img_path.length; i++){
        feedHtmlMid += `<img src="${img_path[i]}" width="400px" height="300px">`
    }
    return feedHtmlStart + feedHtmlMid + feedHtmlEnd;
}