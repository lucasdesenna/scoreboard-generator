# Scoreboard Generator

Parses a properly formatted invitation list file (.txt) into a customer scoreboard and exposes it through an HTTP endpoit. Another HTTP endpoint allows for the addition of new invitations to a loaded list.
  
#### Important

Invitation lists must be text files containing a single '$inviter-id $invitee-id' pair per line. IDs must be integers.

#### Sample Input Files

Some sample input files are provided along with the standalone version. They can be found inside the 'Sample Inputs' folder.

## Installation

Download [Java 1.6 or greater] (http://java.sun.com/javase/downloads/index.jsp).

Download [Scoreboard Generator latest version](http://lucasdesenna.github.io/scoreboard-generator/download/scoreboard-generator-0.1.0-standalone.zip).

## Usage
```

  $ java -jar scoreboard-generator-0.1.0-standalone.jar

```
## License

Copyright © 2016 Lucas de Senna Correia

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
