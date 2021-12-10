#!/usr/bin/env ruby

class Instructions
  attr_accessor :floor

  def initialize(input_file)
    @floor = 0
    @instructions = read_file(input_file)[0]
  end

  def read_file(input_file)
    File.readlines(input_file, chomp: true)
  end

  def follow_instructions
    @instructions.split('').each { |c| @floor += c == '(' ? 1 : -1 }
    @floor
  end
end

if __FILE__ == $0
  test = Instructions.new "test_input.txt"
  puts test.follow_instructions

  part1 = Instructions.new "input.txt"
  puts "Answer part 1: #{part1.follow_instructions}"
end