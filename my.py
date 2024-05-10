import yaml

yaml_string = """
components:
  - name: subvalue1
    type: daemon
  - name: subvalue2
    type: daemon
"""

# Convert YAML string to dictionary
yaml_dict = yaml.safe_load(yaml_string)
for var in yaml_dict['components']:
    print(var)
    if var['type']=='daemon':
        print(var)
        yaml_dict['components'].remove(var)

# Print the resulting dictionary
print(yaml_dict)