import org.yaml.snakeyaml.Yaml

def input = '''components:
  - !component
    name: component1
    argv: first
    settings:
      username: hongqi
      password: nopass'''

def parsed = new Yaml().load(input)
println(parsed.dump())
  
