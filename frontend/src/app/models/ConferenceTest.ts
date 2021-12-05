import {Section} from './Section';
import {Program} from './Program';
import {Location} from './Location';
import {LocationUpdate} from './LocationUpdate';
import {ProgramUpdate} from './ProgramUpdate';

export class ConferenceTest {
  name: string;
  location: LocationUpdate;
  program: ProgramUpdate;


  constructor(name: string, location: LocationUpdate, program: ProgramUpdate) {
    this.name = name;
    this.location = location;
    this.program = program;
  }

}
