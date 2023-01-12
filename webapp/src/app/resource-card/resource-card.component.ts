import { Component, Injectable, Input, OnInit, Output } from '@angular/core';
import { UserprofileService } from '../services/userprofile.service';
import { EventEmitter } from '@angular/core';

@Component({
  selector: 'app-resource-card',
  templateUrl: './resource-card.component.html',
  styleUrls: ['./resource-card.component.css']
})


export class ResourceCardComponent implements OnInit {

  @Input() resource: any;
  @Input() expertise: any;
  @Input() indexOfExpertiseArray: any;
  @Input() indexOfResourceArray: any;
  @Output() index = new EventEmitter();
  @Output() count1 = new EventEmitter<any>();



  count: any;
  constructor(private userProfileService: UserprofileService) { }
  userProfile: any;


  ngOnInit(): void {

    var card = this.userProfileService.getProfileById(this.resource.email)
      .subscribe(response => {
        this.userProfile = response;
      },
        error => {
          console.log(error);
        });
    this.findCount();
  }
  findCount() {
    this.count = this.expertise.resourcesSuggested.length - this.expertise.noOfResources / 3;
    this.count1.emit(this.count)
  }

  deleteDev(i, j) {
    const deleteResources = {
      expertiseIndex: this.indexOfExpertiseArray,
      resourceIndex: this.indexOfResourceArray
    }
    this.index.emit(deleteResources);
  }
}
