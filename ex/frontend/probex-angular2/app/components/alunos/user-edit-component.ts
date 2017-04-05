import { Component } from '@angular/core';
import { User } from '../.././models/user';
import { UserService } from '../.././services/user-service';
import { OnInit } from '@angular/core';
import { Router, ActivatedRoute, ROUTER_DIRECTIVES } from '@angular/router'; 

@Component({
	selector: 'user-edit',
	templateUrl: 'app/views/uers/edit.html',
	providers: [ UserService ],
	directives: [ ROUTER_DIRECTIVES ]
})
export class UserEditComponent implements OnInit {

	private id: number;
	private user: User;

	error: string;

	constructor(
		private route: ActivatedRoute, 
		private router: Router, 
		private userService: UserService) {
	}

	ngOnInit() {
		this.id = +this.route.snapshot.params['id'];
		this.userService.findById(this.id).subscribe(
            data => this.user = data,
            error => this.error = "Could not find user"
        );
	}

	update() {
		this.userService.update(this.user).subscribe(
			data => this.router.navigate(['/user-list']),
            error => this.error = "Could not update user"
		);
	}
}