#!/usr/bin/env ruby

class Box
  attr_accessor :length, :width, :height

  def initialize(length, width, height)
    @squares = [length * width, width * height, height * length]
    @dimensions = [length, width, height]
  end

  def get_wrapping_paper_needed
    @squares.sum * 2 + @squares.min
  end

  def get_ribbon_needed
    # [1, 2, 3].inject(:*) => multiply all elements in array
    @dimensions.sort[0..1].sum * 2 + @dimensions.inject(:*)
  end
end

class WrappingFactory

  def initialize(input_file)
    init(input_file)
  end

  def init(input_file)
    @boxes = []
    File.readlines(input_file, chomp: true).each{ |line| 
      l, w, h = line.split("x").map(&:to_i)
      @boxes << Box.new(l, w, h)
    }
  end

  def get_wrapping_paper_needed
    # Sum the required feet of wrapping for all boxes
    @boxes.inject(0) { |total, box| total + box.get_wrapping_paper_needed }
  end

  def get_ribbon_needed
    @boxes.inject(0) { |total, box| total + box.get_ribbon_needed }
  end 

end

if __FILE__ == $0

  test1 = Box.new(2, 3, 4)
  puts "Box 2x3x4 should need 58sq feet: #{test1.get_wrapping_paper_needed}"

  test2 = Box.new(1, 1, 10)
  puts "Box 1x1x10 should need 43sq feet: #{test2.get_wrapping_paper_needed}"

  factory = WrappingFactory.new("input.txt")
  puts "Answer part 1: #{factory.get_wrapping_paper_needed}"

  puts "Box 2x3x4 requires 34 feet of ribbon: #{test1.get_ribbon_needed}"
  puts "Box 1x1x10 requires 14 feet of ribbon: #{test2.get_ribbon_needed}"

  puts "Answer part 2: #{factory.get_ribbon_needed}"

end
