#!/bin/zsh
echo "Running the Luego applications"

function runCommand() {
    for d in ./*/ ; do /bin/zsh -c "(cd "$d" && echo && echo '================================' && echo 'RUNNING' "$d" && echo && "$@")"; done
}

runCommand "mvn test"