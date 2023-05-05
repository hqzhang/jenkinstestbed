// vars/buildPlugin.groovy
def call(String cmd, String directory){
    ProcessBuilder procBuilder = new ProcessBuilder("bash", "-c", cmd);
    procBuilder.directory(new File(directory))
    procBuilder.redirectErrorStream(true);
    def proc = procBuilder.start()
    proc.waitFor()
    def err=proc.exitValue()
    def reader = new BufferedReader(new InputStreamReader(proc.getInputStream()))
    def line = null
    def out=''
    while ((line = reader.readLine()) != null) { out += line+ "\n" }
    if ( err != 0){
        println("ERROR: $out")
        error("Debug for cmd:$cmd")
    }
    return out
}


