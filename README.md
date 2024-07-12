```
GET /rest/api/1.0/projects/{projectKey}/repos/{repositorySlug}/files/{path}?at=refs/heads/{branchName}
GET /rest/api/1.0/projects/{projectKey}/repos/{repositorySlug}/browse/{path}?at=refs/heads/{branchName}
Get-Command -Name groovy | Select-Object -ExpandProperty Path
Get-Command -Name java | Select-Object -ExpandProperty Path
Return """
 def ret=[]
def myrepo='https://fredzhang123@bitbucket.org/wave-cloud/groovytest.git'+repoName+'.git'
def out = "git ls-remote --heads https://fredzhang123@bitbucket.org/wave-cloud/groovytest.git".execute().text
out = out.readLines().collect { it.split()[1].replaceAll('refs/heads/', '') }
out.each{ if( it.contains('') ){ ret.add(0,it) } else { ret.add(it) } }
return ret
curl -L \
  -X PUT \
  -H "Accept: application/vnd.github+json" \
  -H "Authorization: Bearer <YOUR-TOKEN>" \
  -H "X-GitHub-Api-Version: 2022-11-28" \
  https://api.github.com/repos/OWNER/REPO/contents/PATH \
  -d '{"message":"my commit message","committer":{"name":"Monalisa Octocat","email":"octocat@github.com"},"content":"bXkgbmV3IGZpbGUgY29udGVudHM="}'
```
