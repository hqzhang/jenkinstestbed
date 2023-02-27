#!/usr/bin/env groovy
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.transform.Field
def copyFile(String srcFile, String destFile){
    def sourcePath = Paths.get(srcFile)
    def destinationPath = Paths.get(destFile)
    Files.copy(sourcePath, destinationPath,StandardCopyOption.REPLACE_EXISTING)
}
USERNAME='fredzhang'
PASSWORD='ATBBFSVPkWdLh9LjtZpc2wWXeNqADB6891B3'

def executeCmd(String cmd, String directory){
    ProcessBuilder procBuilder = new ProcessBuilder("bash", "-c", cmd);
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
def gitPrep(String workbr, String mergebr, String directory){
    
    def command = "git branch -r ;git checkout $workbr; git branch; git pull origin $mergebr"
    def output = executeCmd(command, directory)
    
    return output
}
/****
 sh """rm -rf $repo; git clone https://${USERNAME}:${PASSWORD}@bitbucket.org/hqzhang/myrepo.git -b $workbr
                                               
        git remote set-url origin https://${USERNAME}:${PASSWORD}@bitbucket.org/hqzhang/myrepo.git 
                            """
***/
def gitFinal(String src, String workbr, String mergebr, String directory){
    println "enter gitFinal()"
    def dest="$directory/CI.yml"
    def token='ATBBFSVPkWdLh9LjtZpc2wWXeNqADB6891B3'
    def cmd="""pwd; rm -rf upload-test; git clone https://fredzhang123:$token@bitbucket.org/wave-cloud/upload-test.git -b workbr ."""
    println cmd
    //def out= executeCmd(cmd, directory)
    cmd="""pwd; git remote set-url origin https://fredzhang123:$token@bitbucket.org/wave-cloud/upload-test.git """
    println cmd
    def out = executeCmd(cmd, directory)

    println "git preparation"
    cmd = "git branch -r ;git checkout $workbr; git pull origin $mergebr"
    out = executeCmd(cmd, directory)
     
    println "git copy update.."
    copyFile(src, dest)

    println "git push ..."
    cmd = "git status; git add . ; git commit -m up; git push"
    out = executeCmd(cmd, directory)
   
    return out
}
//@NonCPS
def getPrid(String repoPR){
    def cmd="curl -u $USERNAME:$PASSWORD -X GET ${repoPR}?state=OPEN "
    def output=executeCmd(cmd)
    def json=new JsonSlurper()
    def obj=json.parseText(output)
    println obj.values[0].id
}
//@NonCPS
def getVersion(String repoPR){
    def cmd="curl -u $USERNAME:$PASSWOR -X GET ${repoPR}?state=OPEN "
    def output=executeCmd(cmd)
    def json=new JsonSlurper()
    def obj=json.parseText(output)
    return obj.values[0].version
}
//@NonCPS
def getMergestatus(String repoPR, int prid){
    def cmd="curl -u $USERNAME:$PASSWOR -X GET ${repoPR}/$prid/merge"
    def output=executeCmd(cmd)
    def json=new JsonSlurper()
    def obj=json.parseText(output)
    return obj.canMerge
}


def createPR(String workbr, String mergebr,String project, String repo){
    def repoPR="https://bitbucket.org/rest/api/1.0/project/$project/repos/$repo/pull-requests"
    def data=[ 
       title: 'PR-testing',
       description: null,
       state:  'OPEN', open: true,
       closed: false,
       fromRef: [ id:  "refs/heads/$workbr",
                  repository: [ slug: "$repo", name: null,
                                  project: [ key: "$project" ]
                                ]
                ],
        toRef: [ id: "refs/heads/$mergebr",
                 repository: [ slug: "$repo",
                               name: null,
                               project: [key: "$project"]
                   ]
                ],
        locked: false,
        reviewers: [] ]
        def body=JsonOutput.toJson(JsonOutput.toJson(data))
        def cmd="""curl -u $USERNAME:$PASSWORD -X POST -H "Content-Type: applicatin/json" $repoPR --data $body"""
        def output=executeCmd(cmd)
        def json=new JsonSlurper()
        def obj=json.parseText(output)
        return obj.id
}


def mergePR(String repoPR, int prid, int version){
    //def prid=getPrid(repPR)
    println prid
    //def version=getVersion()
    println version
    def cmd="""curl -u $USERNAME:$PASSWOR -X POST -H "Content-Type: applicatin/json" $repoPR/$prid/merge?version=$version"""
   
    def output=executeCmd(cmd)
    println output
}
    def workbr='workbr'
    def mergebr='master'
    def dir='/tmp/upload-test'
    def src='/tmp/CI.yml'
    def project="GRP"
    def repo="upload-test"
    def repoPR="https://bitbucket.org/rest/api/1.0/project/$project/repos/$repo/pull-requests"

//gitUpdate(src, workbr, mergebr, dir)
//execusteCmdErr()()
gitFinal(src,workbr, mergebr,dir)
createPR(workbr, mergebr, project, repo)
//mergePR(repoPR,  prid, version){