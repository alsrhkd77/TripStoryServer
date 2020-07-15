let curUserId = null;
let curUserName = null;

function doGoogleLogin() {
    const provider = new firebase.auth.GoogleAuthProvider();
    provider.addScope('profile');
    provider.addScope('email');
    firebase.auth().signInWithPopup(provider).then(function (result) {
        curUserId = result['additionalUserInfo'].profile.id;
        curUserName = result['additionalUserInfo'].profile.name;
        doLogin(curUserId, curUserName);
    });
}

function doFacebookLogin() {
    const provider = new firebase.auth.FacebookAuthProvider();
    firebase.auth().signInWithPopup(provider).then(function (result) {
        curUserId = result['additionalUserInfo'].profile.id;
        curUserName = result['additionalUserInfo'].profile.name;
        doLogin(curUserId, curUserName);

    });
}

function doLogin(id, name) {
    const profile = {
        id: id,
        name: name
    };
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/login-social', true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            if (xhr.responseText === 'login-success') {
                window.location.href = "/";
            }else{
                alert("login Error!");
            }
        }
    };
    xhr.send(JSON.stringify(profile));
}