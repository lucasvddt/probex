"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var http_1 = require("@angular/http");
var Rx_1 = require("rxjs/Rx");
var SigninService = (function () {
    function SigninService(http) {
        this.http = http;
        this.serviceUrl = "http://localhost:8080/ProbexService/rest/signIn";
        this.loggedUser = new Rx_1.BehaviorSubject("");
    }
    SigninService.prototype.signIn = function (username, password) {
        var _this = this;
        this.validateAndGetUser(username, password).subscribe(function (data) {
            _this.user = data;
            if (_this.user != null) {
                console.log("succesful");
                sessionStorage['username'] = _this.user.username;
                _this.loggedUser.next(_this.user.username);
            }
        }, function (error) {
            _this.error = "Could not sign in";
            console.log("could not sign in");
        });
    };
    SigninService.prototype.signOut = function () {
        delete sessionStorage['username'];
        this.loggedUser.next("");
        this.router.navigate(['/signIn']);
    };
    SigninService.signedIn = function () {
        return sessionStorage['username'] != null;
    };
    SigninService.prototype.validateAndGetUser = function (username, password) {
        var url = this.serviceUrl + '/u=' + username + '-p=' + password;
        return this.http.get(url).map(function (res) { return res.json(); });
    };
    return SigninService;
}());
SigninService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http])
], SigninService);
exports.SigninService = SigninService;
//# sourceMappingURL=signin-service.js.map