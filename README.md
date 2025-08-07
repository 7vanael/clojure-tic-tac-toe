# Tic-Tac-Toe

A Clojure implementation of Tic-Tac-Toe with multiple interface and save options.

## Installation

Clone the repository and ensure you have [Leiningen](https://leiningen.org/) installed.

Postgres is required if you intend to save to a database. 

`brew install postgresql`

`brew services start postgresql`

Ensure required dependencies are loaded 

`lein deps`

## Usage

### Basic Usage

Run the game with default settings (TUI interface, SQL save):

`
lein run
`

### Specs
`lein spec -a`
will auto run tests for Clojure tests

`lein cljs`
will auto run tests for ClojureScript 

Common code is tested in both batches.


### Command Line Options

| Description | Valid Values         | Default |
|-------------|----------------------|---------|
| Choose the game interface | `--tui`, `--gui`     | `tui` |
| Choose how game data is saved | `--sql`, `--edn`     | `sql` |

### Examples

```
# Use text-based interface with SQL database (defaults)
lein run

# Use graphical interface with default SQL database
lein run --gui

# Use default text interface with EDN file storage
lein run --edn

# Use graphical interface with EDN file storage
lein run --gui --edn
(or: lein run --edn --gui)

Flags are order agnostic, but if you duplicate flag type, 
the last flag entered will be used.
```

### Interface Types

- `tui`: (Text User Interface): Play in the terminal with text-based prompts
- `gui`: (Graphical User Interface): Play with a graphical window interface using Quil

### Save Types

- `sql`: Save game data to a SQL database (PostgreSQL)
- `edn`: Save game data to a local file

### Web Server option
To compile ClojureScript and run tests, run: 

`lein cljs`

Then, to view on localhost:8080 with command: 
`python3 -m http.server 8080`
after navigating to resources/public

(then visit localhost:8080 in your browser)

Save options are not available in the static web page version, but is 
in both Quil --gui and CLI --tui
