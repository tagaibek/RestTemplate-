/*
$(document).ready(function () {
    $.get('/api/users', function users(users) {
        for (const user of users) {
            users_table(user);
        }
    });
});

$(document).ready(function () {
    $("#add_form").submit(function (event) {
        event.preventDefault();
        fire_ajax_submit();
    });
});

function fire_ajax_submit() {
    $.ajax({
        url: "/api/user",
        type: 'POST',
        data: $("#add_form").serialize(),
        success: function (user) {
            users_table(user);
            $('#users-tab').trigger('click');
        }
    });
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
    for (const role of user.roles) {
        tdRoles.text(role.authority);
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
function editUser(id){
    $.ajax({
        url: "/api/user/"  +  id,
        type: 'GET',
        success: function (user) {
            $('#id_edit').val(user.id);
            $('#username_edit').val(user.username);
            $('#password_edit').val(user.password);
            $('#roles_edit').val(user.roles);
            $.ajax($('.modal').modal('show'))
        }
    });
}
$(document).ready(function() {
    $("#update-user").submit(function (event){
        event.preventDefault ();
        edit_ajax_submit();
    });
});
function edit_ajax_submit() {
    $.ajax({
        url: "/api/user/update",
        type: 'PUT',
        data: $("#update-user").serialize(),
        success: function (user) {
            $('#user-list tr#'+user.id).remove();
            users_table(user);
            $('#close_edit').trigger('click');
            $('#user-list').trigger('click');
        }
    });
}*/