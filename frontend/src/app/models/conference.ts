import {selector} from 'rxjs/operator/publish';
import {Section} from './section';
import {Program} from './Program';
import {Location} from './Location';

export class Conference {
  id: string;
  name: string;
  location: Location;
  section: Section;
  program: Program;
  programCommittee: string[];
  participants: string[];
  speakers: string[];


  constructor(id: string, name: string, location: Location, section: Section, program: Program, programCommittee: string[], participants: string[], speakers: string[]) {
    this.id = id;
    this.name = name;
    this.location = location;
    this.section = section;
    this.program = program;
    this.programCommittee = programCommittee;
    this.participants = participants;
    this.speakers = speakers;
  }

}
