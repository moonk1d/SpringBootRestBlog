function PostsViewModel() {
        var self = this;
        self.postsURI = 'http://localhost:8080/api/posts';
        self.username = "user";
        self.password = "user";
        self.posts = ko.observableArray();

        self.ajax = function(uri, method, data) {
            var request = {
                url: uri,
                type: method,
                contentType: "application/json",
//                accepts: "application/json",
                cache: false,
                dataType: 'json',
                data: JSON.stringify(data),
//                beforeSend: function (xhr) {
//                    xhr.setRequestHeader("Authorization",
//                        "Basic " + btoa(self.username + ":" + self.password));
//                },
                error: function(jqXHR) {
                    console.log("ajax error " + jqXHR.status);
                }
            };
            return $.ajax(request);
        }

        self.beginAdd = function() {
            $('#add').modal('show');
        }
        self.beginEdit = function(post) {
            alert("Edit: " + post.title());
        }
        self.remove = function(post) {
            alert("Remove: " + post.title());
        }

        self.ajax(self.postsURI, 'GET').done(function(data) {
            for (var i = 0; i < data.posts.length; i++) {
                self.posts.push({
                    //uri: ko.observable(data[i].uri),
                    title: ko.observable(data.posts[i].title),
                    body: ko.observable(data.posts[i].body)
                });
            }
        });

        self.add = function(post) {
                self.ajax(self.postsURI, 'POST', post).done(function(data) {
                    self.posts.push({
                        //uri: ko.observable(data.task.uri),
                        title: ko.observable(data.post.title),
                        body: ko.observable(data.post.body)
                    });
                });
            }
    }

function AddPostViewModel() {
        var self = this;
        self.title = ko.observable();
        self.body = ko.observable();

        self.addPost = function() {
            $('#add').modal('hide');
            postsViewModel.add({
                title: self.title(),
                body: self.body()
            });
            self.title("");
            self.body("");
        }
    }

    var postsViewModel = new PostsViewModel();
    var addPostViewModel = new AddPostViewModel();
    ko.applyBindings(postsViewModel, $('#main')[0]);
    ko.applyBindings(addPostViewModel, $('#add')[0])