$(document).ready(function () {
    $.get('/api/users', function users(users) {
        for (const user of users) {
            users_table(user);
        }
    });
});
function  users_table(user) {
    let tr = $(document.createElement('tr'));
    tr.attr('id', user.id);
    let tdUsername = $(document.createElement('td'));
    tdUsername.text(user.username);
    tr.append(tdUsername);

    let tdPassword = $(document.createElement('td'));
    tdPassword.text(user.password);
    tr.append(tdPassword);

    let tdRoles = $(document.createElement('td'));
    let a = $(document.createElement("a"))
    for (const role of user.roles) {
        a.append(role.authority+" ;  ");
        tdRoles.append(a);
    }
    tr.append(tdRoles);

    let tdEdit = $(document.createElement('td'));
    tdEdit.append("<button type=\"button\" class=\"btn btn-primary\" id='edit' onclick=editUser("+user.id+")>" +
        "<i class=\"fas fa-user-edit ml-2\"></i> Edit</button>");
    tr.append(tdEdit);

    let tdDelete = $(document.createElement('td'));
    tdDelete.append("<a class=\"btn btn-primary\" id='delete'  onclick=deleteUser("+ user.id +")>" +
        "<i class=\"fas fa-user-times ml-2\"></i></a>");

    tr.append(tdDelete);

    $('#user-list').append(tr);
}

function deleteUser(id) {
    $.ajax({
        url: "/api/user/"  +  id,
        type: 'DELETE',
        success: function (data, textStatus, xhr) {
            if (xhr.status === 200){

                $('#user-list tr#'+id).remove();
                $('#user-list').trigger('click');

            }
        }
    });
}

$(document).ready(function () {
    $("#add_user").on('click',function () {
        var userRoles;
        var rolesArray = [];
        $('.check').each(function(){
            if (this.checked){
                rolesArray.push(this.value);
            }
        });

        userRoles = JSON.parse(JSON.stringify(rolesArray));
        var addUser = {
            username: $("#new_username").val(),
            password: $("#new_password").val(),
            roles: userRoles
        };
        $.ajax({
            url: "/api/user",
            type: 'POST',
            data: JSON.stringify(addUser),
            contentType: "application/json",
            success: function (user) {
                users_table(user);
                $('#users-tab').trigger('click');
            }
        });
    });
});



function editUser(id){
    $.ajax({
        url: "/api/user/"  +  id,
        type: 'GET',
        success: function (user) {
            $('#id_edit').val(user.id);
            $('#username_edit').val(user.username);
            $('#password_edit').val(user.password);
            $.ajax($('.modal').modal('show'))
        }
    });
}
$(document).ready(function() {
    $("#update-user").submit(function (){
        var userRoles;
        var rolesArray = [];
        $('.check_box').each(function(){
            if (this.checked){
                rolesArray.push(this.value);
            }
        });

        userRoles = JSON.parse(JSON.stringify(rolesArray));
        var addUser = {
            id: $("#id_edit").val(),
            username: $("#username_edit").val(),
            password: $("#password_edit").val(),
            roles: userRoles
        };
        console.log(addUser.id);
        $.ajax({
            url: "/api/user/update",
            type: 'PUT',
            data: JSON.stringify(addUser),
            contentType: "application/json",
            success: function (user) {
                users_table(user);
                $('#users-tab').trigger('click');
                $('#user-list tr#'+user.id).remove();
                users_table(user);
                $('#close_edit').trigger('click');
                $('#user-list').trigger('click');
            }
        });
    });
});
