# A sample Guardfile
# More info at https://github.com/guard/guard#readme

# Add files and commands to this file, like the example:
#   watch(%r{file/path}) { `command(s)` }
#
#
guard 'shell' do
  watch(%r{^resources/templates/(.*)/(.*)\.html$}) {|m| `touch src/exchange/views/#{m[1]}.clj` }
end
