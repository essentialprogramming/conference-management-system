import { NgModule, Component } from '@angular/core';

import {RouterModule, Routes} from '@angular/router';
import { ProposalFormComponent } from '../speaker/proposal-form/proposal-form.component';

import {AuthorComponent} from '../speaker/author.component';
import {ConferenceChairComponent} from '../conference-chair/conference-chair.component';
import {ReviewerComponent} from '../reviewer/reviewer.component';
import { AppComponent } from '../app.component';
import {MySubmissionsComponent} from '../speaker/my-submissions/my-submissions.component';
import {GiveQualifierComponent} from '../reviewer/give-qualifier/give-qualifier.component';
import {BidProposalComponent} from '../reviewer/bid-proposal/bid-proposal.component';
import {AddConferenceComponent} from '../conference-chair/add-conference/add-conference.component';
import {AssignPapersComponent} from '../conference-chair/assign-papers/assign-papers.component';
import { HomePageComponent } from '../home-page/home-page.component';
import { AuthGuardService } from '../Guards/auth-guard.service';
import {SectionComponent} from '../conference-chair/section/section.component';
import {AssignSectionComponent} from '../conference-chair/assign-section/assign-section.component';
import {AssignSupervisorComponent} from '../conference-chair/assign-supervisor/assign-supervisor.component';
import { SpeakerGuardService } from '../Guards/speaker-guard.service';
import { ChairGuardService } from '../Guards/chair-guard.service';
import { ChoseRoleUserComponent } from '../chose-role-user/chose-role-user.component';
import { ParticipantComponent } from '../participant/participant.component';
import { AllPapersComponent } from '../speaker/all-papers/all-papers.component';
import {AssignReviewerEventComponent} from '../conference-chair/assign-reviewer-event/assign-reviewer-event.component';


const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: 'Event', component: ParticipantComponent,runGuardsAndResolvers: 'always'
  },
  {
    path: 'home', component: HomePageComponent,runGuardsAndResolvers: 'always'
  },
  {
    path: 'logIn', component: ChoseRoleUserComponent,runGuardsAndResolvers: 'always'
  },
  {
    path: 'home/:id', component: HomePageComponent, runGuardsAndResolvers: 'always'
  },

// reviwer
      {
      path: 'reviewer', component: ReviewerComponent, canActivate:[AuthGuardService]

      },
      {
      path: 'giveQualifier', component: GiveQualifierComponent, canActivate:[AuthGuardService]
      },

      {
      path: 'bidProposal', component: BidProposalComponent, canActivate:[AuthGuardService]
      },


// author
      {
      path: 'submitProposal', component: ProposalFormComponent, canActivate:[SpeakerGuardService]
      },
      {
        path: 'submitProposal/:id', component: ProposalFormComponent, canActivate:[SpeakerGuardService]
        },
      {
      path: 'mysubmissions', component: MySubmissionsComponent, canActivate:[SpeakerGuardService]
      },
      {
        path: 'allPapers', component: AllPapersComponent, canActivate:[SpeakerGuardService]
        },
      {path: 'speaker', component: AuthorComponent , canActivate:[SpeakerGuardService]

      },

      // chair
      {
        path: 'addConference', component: AddConferenceComponent, canActivate:[ChairGuardService]
      },

      {
        path: 'assignPapers', component: AssignPapersComponent, canActivate:[ChairGuardService]
      },
      {
        path: 'createSection', component: SectionComponent, canActivate:[ChairGuardService]
      },

      {
        path: 'assignSection', component: AssignSectionComponent, canActivate:[ChairGuardService]
      },
      {
        path: 'assignSupervisor', component: AssignSupervisorComponent, canActivate:[ChairGuardService]
      },
  {
    path: 'assignReviewerEvent', component: AssignReviewerEventComponent, canActivate:[ChairGuardService]
  },
  {
    path: 'conferenceChair', component: ConferenceChairComponent, canActivate:[ChairGuardService]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]

})
export class AppRoutingModule {
}
