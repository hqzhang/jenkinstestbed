#!/usr/bin/env groovy
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
def copyFile(String srcFile, String destFile){
    def sourcePath = Paths.get(srcFile)
    def destinationPath = Paths.get(destFile)
    Files.copy(sourcePath, destinationPath,StandardCopyOption.REPLACE_EXISTING)
}
dir='/Users/hongqizhang/workspace/test1'
def execusteCmdErr(){
    ProcessBuilder pb = new ProcessBuilder("git status");
    pb.directory(new File('/Users/hongqizhang/workspace/test1'))
    pb.redirectErrorStream(true);
    Process process = pb.start();
    InputStream stdout = process.getInputStream();
    InputStream stderr = process.getErrorStream();

    // Read and print the output of the process
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(stdout))) {
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

    // Read and print the error output of the process
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(stderr))) {
        String line;
        while ((line = reader.readLine()) != null) {
            System.err.println(line);
        }
    }
}
def exeCmd(String cmd, String directory){
    def command = cmd.split()
    def procBuilder = new ProcessBuilder(command)
    procBuilder.directory(new File(directory))
    procBuilder.redirectErrorStream(true);
    def proc = procBuilder.start()
    proc.waitFor()
    
    def err=proc.exitValue()
    def reader = new BufferedReader(new InputStreamReader(proc.getInputStream()))
   
    def line = null
    def output=''
    while ((line = reader.readLine()) != null) {
        output = output +line+ "\n"
    }
    
    println( "-----------------")
    println("exitValue: " + err)
    println( "-----------------")
    return output
}
def exeCmd1( String directory){
    println "enter exeCmd1()"
    //def command = cmd.split()
    ProcessBuilder procBuilder = new ProcessBuilder("bash", "-c", "git status; git add -u .; git push");

    procBuilder.directory(new File(directory))
    procBuilder.redirectErrorStream(true);
    def proc = procBuilder.start()
    proc.waitFor()
    
    def err=proc.exitValue()
    def reader = new BufferedReader(new InputStreamReader(proc.getInputStream()))
   
    def line = null
    def output=''
    while ((line = reader.readLine()) != null) {
        output = output +line+ "\n"
    }
    
    println( "-----------------")
    println("exitValue: " + err)
    println( "-----------------")
    println output
    return output
}
/*
def gitCmd(String directory){
    println directory
    cmd="bash", "-c", "ls ; pwd"
    def out=exeCmd1(cmd,directory)
    println out
    return out
}*/
//@NonCPS
def gitStatus(String directory){
    def cmd="git status";
    def output=exeCmd(cmd,directory)
    return output
}
//@NonCPS
def gitAddall(String directory){
    def cmd="git add -u .";
    def output=exeCmd(cmd,directory)
    return output
}
//@NonCPS
def gitCheckout(String branch,String directory){
    println directory
    def cmd="git checkout $branch"
    def output=exeCmd(cmd,directory)
    return output
}
//@NonCPS
def gitBranch(String directory){
    def cmd="git branch"
    def output=exeCmd(cmd,directory)
    return output
}
//@NonCPS
def gitBranchrem(String directory){
    def cmd="git branch -r"
    def output=exeCmd(cmd,directory)
    return output
}
//@NonCPS
def gitPullbr(String branch, String directory){
    println directory
    def cmd="git pull origin $branch"
    def output=exeCmd(cmd,directory)
    return output
}


//@NonCPS
def gitCommit(String directory, String msg='update'){
    println directory
    def cmd = "git commit -m $msg"
    def output=exeCmd(cmd,directory)
    return output
}
//@NonCPS
def gitPushf(String directory){
    println directory
    def cmd="git push -f"
    def output=exeCmd(cmd,directory)
    return output
}

def gitUpdate(String src, String workbr, String mergebr, String dir){
    def dest="$dir/CI.yml"

    println "git status"
    gitStatus(dir)
    
    println "git branch -r"
    gitBranchrem( dir)

    println "git checkout $workbr"
    gitCheckout(workbr,dir )
   
    println"git branch"
    gitBranch(dir)
  
    println "git pull.."
    gitPullbr(mergebr,dir)

    println "git copy update.."
    copyFile(src, dest)

    println "git add .."
    gitAddall(dir)
    
    println "git commit.."
    gitCommit(dir)

    println "git push"
    gitPushf(dir)

}
    def workbr='feature/test1'
    def mergebr='master'
    def dir='/Users/hongqizhang/workspace/test1'
    def src='/tmp/CI.yml'
//gitUpdate(src, workbr, mergebr, dir)
//execusteCmdErr()()
exeCmd1(dir)