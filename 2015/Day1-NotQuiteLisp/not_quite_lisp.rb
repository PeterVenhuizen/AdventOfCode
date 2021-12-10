#!/usr/bin/env ruby

class Instructions

  def initialize(input_file)
    @instructions = read_file(input_file)[0]
  end

  def read_file(input_file)
    File.readlines(input_file, chomp: true)
  end

  def follow_instructions
    floor = 0
    @instructions.each_char { |c| 
      floor += c == '(' ? 1 : -1 
    }
    return floor
  end

  def when_do_I_enter_the_basement
    floor = 0
    @instructions.each_char.with_index(1) { |char, i|
      floor += char == '(' ? 1 : -1
      if floor == -1
        return i
      end
    }
  end
end

if __FILE__ == $0
  test = Instructions.new "test_input.txt"
  puts test.follow_instructions

  answer = Instructions.new "input.txt"
  puts "Answer part 1: #{answer.follow_instructions}"

  puts "Answer part 2: #{answer.when_do_I_enter_the_basement}"
end